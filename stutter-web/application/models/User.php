<?php
//Model for handling users     
class User extends CI_Model 
{

    function __construct()
    {
        parent::__construct();
    }

//Validates a user   
    function validate_user()
    {
        $user = array(
            'user_name' => $_POST['user_name'],
            'password'  => $_POST['password']
        );
        $this->db->where($user);
        $query = $this->db->get('users');
        if($query->result()[0]->id)
        {
            return $query->result()[0]->imei;
        }
        else
        {
            return FALSE;    
        }
    }
    
//Registers a new user 
    function register_user()
    {
        $user = array(
            'user_name'=> $_POST['user_name'],
            'password' => $_POST['password'],
            'imei'     => $_POST['imei'],
            'email'    => $_POST['email']
        );

        $this->db->insert('users', $user);
    }

    function update_user()
    {
        // $this->title   = $_POST['title'];
        // $this->content = $_POST['content'];
        // $this->date    = time();

        // $this->db->update('entries', $this, array('id' => $_POST['id']));
    }

}