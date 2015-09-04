<?php
//Model for handling users     
class Upload extends CI_Model 
{

    function __construct()
    {
        parent::__construct();
    }
	
	public function save_file($uploader_id,$file_name)
	{
		$file = array(
            'uploader_id' => $uploader_id,
            'file_name'   => $file_name
        );

        $this->db->insert('uploads', $file);
	}
}