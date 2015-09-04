<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see http://codeigniter.com/user_guide/general/urls.html
	 */
	public function index()
	{
		if($this->session->userdata('user_id'))
		{
			$this->load->model('Upload');
			$data['results'] = $this->Upload->get_user_files();
			$this->load->view('header');
			$this->load->view('navbar');
			$this->load->view('welcome_page',$data);
			$this->load->view('footer');
			//var_dump($results);
		}
		else
		{
			redirect('users/login');
		}
	}
	
	public function play($file_name)
	{
		$fileloc = 'audio.mp3';
		header('Content-type: audio/mpeg');
		header("Content-disposition: inline; filename=$filename");
		header('Content-Length:'.filesize($fileloc));
		readfile('uploads/'.$file_name);
	}

}
