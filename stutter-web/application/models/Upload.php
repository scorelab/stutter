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

    public function get_all_files()
    {
        $query = $this->db->get('uploads');
        return $query->result();
    }

    public function get_user_files()
    {
        $user = array(
            'uploader_id' => $this->session->userdata('user_id')
        );
        $this->db->where($user);
        $query = $this->db->get('uploads');
        return $query->result();
    }

    public function auth_file_play($file_name)
    {
      $user = array(
          'uploader_id' => $this->session->userdata('user_id'),
          'file_name'   => $file_name
      );
      $this->db->where($user);
      $query = $this->db->get('uploads');
      return $query->result();
    }

    public function delete_file($file_name)
    {
      $file = array(
          'file_name'   => $file_name
      );
      $this->db->where($file);
      $query = $this->db->delete('uploads');
      return $query;
    }

    public function delete_user_file($file_name)
    {
      $user = array(
          'uploader_id' => $this->session->userdata('user_id'),
          'file_name'   => $file_name
      );
      $this->db->where($user);
      $query = $this->db->delete('uploads');
      return $query->result();
    }
}
