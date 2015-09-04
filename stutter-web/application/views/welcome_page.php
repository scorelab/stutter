<div class="container">
  <div class="page-header">
    <h1 id="navbar">Stutter Aid</h1>
  </div>
  <div class="panel panel-primary">
    <div class="panel-heading">
      <h3 class="panel-title">My Uploaded Files</h3>
    </div>
    <div class="panel-body">
      <table class="table table-striped table-hover ">
        <thead>
          <tr>
            <th>Audio Clip</th>
            <th>Upload Date</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
  <?php foreach($results as $result)
        {?>
          <tr>
            <td><?php echo $result->file_name;?></td>
            <td><?php echo $result->time_stamp;?></td>
            <td>
              <a href="<?php echo base_url('/home/play').$result->file_name;?>">
                play
              </a>
            </td>
            <td><a href="#">delete</a></td>
          </tr>
 <?php  }?>
        </tbody>
      </table>
    </div>
  </div>
</div>
