package se.uu.daftest;

import java.io.FileOutputStream;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder.AudioSource;
import android.os.Environment;
import android.util.Log;

public class DAFProcessor implements Runnable {

    private static final int DEFAULT_RW_SIZE = 256;

    private static final int DEFAULT_SAMPLE_RATE = 48000;

    private static final int MAX_DEALY = 1000;

    private static final int MIN_DEALY = 0;
    
    public static long realDelay=0;

    /**
     * This is set to true if DAF processing is active
     */
    private boolean isActive = false;

    /**
     * Object for processing thread
     */
    private Thread processingThread = null;

    /**
     * This object is used to lock bufReadOffset and bufWriteOffset
     */
    private Object lockObject = null;

    /**
     * This is set to true if DAFProcessor is initialized
     */
    private boolean isInitialized = false;

    /**
     * An object for recording audio
     */
    AudioRecord recorder = null;

    /**
     * An object for playing audio data
     */
    AudioTrack track = null;

    /**
     * The buffer for audio samples
     */
    short[] dataBuffer = null;

    /**
     * The delay to add
     */
    int delay = MAX_DEALY;

    /**
     * Sample rate for recording and playing audio
     */
    int sampleRate = 48000;

    /**
     * Minimum size of the buffer that is required before overflowing data.
     */
    int recordMinBufSize = 256;

    /**
     * Minimum size of the buffer that is required for AudioTrack before running
     * out of data.
     */
    int playMinBufSize = 512;

    /**
     * The offset of the buffer for writing
     */
    int bufWriteOffset = 0;

    /**
     * The offset of the buffer for reading.
     */
    int bufReadOffset = 0;

    /**
     * The size of data to be read or written from/to buffer at a time.
     */
    int bufRWSize = 512;
    
    /**
     * File output stream
     */
    FileOutputStream os=null;
     

    public DAFProcessor() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        isInitialized = false;
        delay = MIN_DEALY;
        sampleRate = DEFAULT_SAMPLE_RATE;
        recordMinBufSize = DEFAULT_RW_SIZE;
        playMinBufSize = DEFAULT_RW_SIZE;
        bufRWSize = DEFAULT_RW_SIZE;
        lockObject = new Object();
    }

    /**
     * Start processing
     */
    public void StartProcessing() {
        if (isActive == true) {
            Log.i(this.getClass().getName(), "Already active");
            return;
        } else {
            processingThread = new Thread(this);
            processingThread.start();
            isActive = true;
        }
    }

    /**
     * Stop processing
     */
    public void StopProcessing() {
        if (isActive == false) {
            Log.i(this.getClass().getName(), "Already stopped");
            return;
        } else {
            isActive = false;
            try {
                processingThread.join();
            } catch (InterruptedException e) {
                Log.w(this.getClass().getName(), e);
            }
            processingThread = null;
        }

    }

    /**
     * Return whether the processor is processing
     * 
     * @return true of if processing
     */
    public boolean IsProcessing() {
        return isActive;
    }

    /**
     * Initialize DAFProcessor
     */
    public void Initialize() {
        Log.i(this.getClass().getName(), "Initializing Audio devices");

        
        Log.i(this.getClass().getName(), "getNativeOutputSampleRate() : " + 
                                          AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_SYSTEM));
        
        recordMinBufSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                                                        AudioFormat.ENCODING_PCM_16BIT);
        Log.i(this.getClass().getName(), "AudioRecord Min Buffer Size " + recordMinBufSize);
        playMinBufSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                                                     AudioFormat.ENCODING_PCM_16BIT);
        Log.i(this.getClass().getName(), "AudioTrack Min Buffer Size " + playMinBufSize);

        // We chose the largest buffer size for smooth operation
        bufRWSize = playMinBufSize > recordMinBufSize ? recordMinBufSize : playMinBufSize;

        int maxDelaySamples = (sampleRate / 1000) * MAX_DEALY;
        dataBuffer = new short[maxDelaySamples + bufRWSize];
    }

    @Override
    public void run() {
        Log.i(this.getClass().getName(), "Running Audio Thread");

        if (isInitialized == false) {
            Initialize();
            SetDelay(0);
            isInitialized = true;
        }

        try {

            recorder = new AudioRecord(AudioSource.DEFAULT, sampleRate, AudioFormat.CHANNEL_IN_MONO,
                                       AudioFormat.ENCODING_PCM_16BIT, bufRWSize * 10);

            track = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                                   AudioFormat.ENCODING_PCM_16BIT, playMinBufSize,
                                   AudioTrack.MODE_STREAM);
            recorder.startRecording();
            track.play();

            int N = 0;
            int writeOffset = 0;
            int readOffset = 0;
            os = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+'/'+"ab.wav");
            while (isActive == true) {

                synchronized (lockObject) {

                    if (dataBuffer.length < bufWriteOffset + bufRWSize) {
                        if (bufWriteOffset > bufReadOffset) {
                            java.lang.System.arraycopy(dataBuffer, bufReadOffset, dataBuffer, 0,
                                                       bufWriteOffset - bufReadOffset);
                            bufWriteOffset = bufWriteOffset - bufReadOffset;
                            bufReadOffset = 0;
                        } else {
                            // / This situation cannot be occurred.
                            bufWriteOffset = 0;
                            bufReadOffset = 0;
                        }
                    }

                    writeOffset = bufWriteOffset;
                    readOffset = bufReadOffset;
                }
                //start time
               // long startTime = System.currentTimeMillis();
                N = recorder.read(dataBuffer, writeOffset, bufRWSize);
                
                if (N != AudioRecord.ERROR_INVALID_OPERATION || N != AudioRecord.ERROR_BAD_VALUE) {
                	
                    track.write(dataBuffer, readOffset, bufRWSize);
                    os.write(short2byte(dataBuffer), readOffset, bufRWSize);
                    //end time
                    //long endTime = System.currentTimeMillis();
                    
                    //display
                    //System.out.println(endTime-startTime);
                    
                    synchronized (lockObject) {
                        bufWriteOffset += bufRWSize;
                        bufReadOffset += bufRWSize;
                    }
                }
            }
            os.close();

        } catch (Throwable x) {
            Log.w(this.getClass().getName(), "Error reading voice audio", x);
        }

        finally {
            recorder.stop();
            recorder.release();
            track.stop();
            track.release();
        }

    }

    /**
     * Set delay
     * 
     * @param millisec
     *            The delay in milliseconds
     */
    public void SetDelay(int millisec) {
        
        if (isInitialized == true) {
            Initialize();
        }
        
        synchronized (lockObject) {
            delay = millisec;

            if (millisec == 0) {
                bufReadOffset = 0;
                bufWriteOffset = 0;
            } else {
                int delaySamples = (sampleRate / 1000) * delay;
                java.util.Arrays.fill(dataBuffer, 0, delaySamples - 1, (short) 0);
                bufReadOffset = 0;
                bufWriteOffset = delaySamples;
            }
        }
    }

    /**
     * Returns the current delay
     * 
     * @return Delay
     */
    public int GetDelay() {
        return delay;
    }

    /**
     * Returns the maximum delay
     * 
     * @return The maximum delay
     */
    public static int GetMaxDealy() {
        return MAX_DEALY;
    }

    /**
     * Returns the minimum delay
     * 
     * @return The minimum delay
     */
    public static int GetMinDealy() {
        return MIN_DEALY;
    }
    
    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }
   

}