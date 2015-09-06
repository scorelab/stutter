<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends CI_Controller
{

	public function index()
	{
		if($this->session->userdata('user_id'))
		{
			if($this->session->userdata('user_type')==1)
			{
				$this->load->model('Upload');
				$data['results'] = $this->Upload->get_all_files();
				$this->load->view('header');
				$this->load->view('navbar');
				$this->load->view('welcome_page',$data);
				$this->load->view('footer');
			}
			else
			{
				$this->load->model('Upload');
				$data['results'] = $this->Upload->get_user_files();
				$this->load->view('header');
				$this->load->view('navbar');
				$this->load->view('welcome_page',$data);
				$this->load->view('footer');
			}
			//var_dump($results);
		}
		else
		{
			redirect('users/login');
		}
	}

	public function play($file_name)
	{
		if($this->session->userdata('user_id'))
		{
			$this->load->model('Upload');
			$result = $this->Upload->auth_file_play($file_name);

			if(isset($result[0]->file_name))
			{
				$fileloc = 'uploads/'.$file_name;
				header('Content-type: audio/mpeg');
				header("Content-disposition: inline; filename=".$fileloc);
				header('Content-Length:'.filesize($fileloc));
				readfile('uploads/'.$file_name);
			}
			else if($this->session->userdata('user_type')==1)
			{
				$fileloc = 'uploads/'.$file_name;
				header('Content-type: audio/mpeg');
				header("Content-disposition: inline; filename=".$fileloc);
				header('Content-Length:'.filesize($fileloc));
				readfile('uploads/'.$file_name);
			}
			else
			{
				redirect('home');
			}
		}
		else
		{
			redirect('home');
		}
	}

	public function delete($file_name)
	{
		if($this->session->userdata('user_id'))
		{
			if($this->session->userdata('user_type')==1)
			{
				$this->load->model('Upload');
				$result = $this->Upload->delete_file($file_name);
				if($result)
				{
					redirect('home');
				}
			}
			else
			{
				$this->load->model('Upload');
				$result = $this->Upload->delete_file($file_name);
				if($result)
				{
					redirect('home');
				}
			}
		}
		else
		{
			redirect('home');
		}
	}

}
