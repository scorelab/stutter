<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Uploads extends CI_Controller
{

	public function index()
	{
		$file_path = "uploads/";
		$file_path = $file_path . basename( $_FILES['uploaded_file']['name']);
		
		$user_id = $_POST['user_id'];
		
		if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path))
		{
			$this->load->model('Upload');
			$result = $this->Upload->save_file($user_id,$_FILES['uploaded_file']['name']);
		}
		else
		{
			echo "false";
		}
	}
}
