<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Users extends CI_Controller {

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
	public function login()
	{
		if(!$this->session->userdata('user_id'))
		{
			if($_POST)
			{
				$this->load->model('User');
				$result = $this->User->validate_user();
				if(isset($result->id))
				{
					$this->session->set_userdata(array(
						'user_id'=> $result->id,
						'user_name' => $result->user_name
						));
					redirect('/home/');
				}
				else
				{
					redirect('/home/');
				}
			}
			else
			{
				$this->load->view('header');
				$this->load->view('login');
				$this->load->view('footer');	
			}
		}
		else
		{
			redirect('/home');
		}
	}
	
	public function logout()
	{
		$result = $this->session->unset_userdata('user_id');
		if($result)
		{
			redirect('/users/login');
		}
		else
		{
			redirect('/home');
		}
	}

}
