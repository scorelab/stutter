LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := daf
LOCAL_SRC_FILES := daf.c

include $(BUILD_SHARED_LIBRARY)
