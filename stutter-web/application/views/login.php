<div class="container">
  <div class="row">
    <div class="col-lg-4 col-lg-offset-4">
      <div class="page-header">
        <h1 style="text-align:center">Stutter Aid</h1>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-lg-4 col-lg-offset-4" >
      <form class="form-horizontal" method="post" action="<?php echo base_url('/users/login')?>">
        <fieldset>
          <div class="form-group">
            <label for="inputEmail" class="col-lg-2 control-label">Email</label>
            <div class="col-lg-10">
              <input type="text" class="form-control" name="user_name" id="inputEmail" placeholder="username">
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword"  class="col-lg-2 control-label">Password</label>
            <div class="col-lg-10">
              <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password">
              <div class="checkbox">
                <label>
                  <input type="checkbox"> Checkbox
                </label>
              </div>
            </div>
            <button type="submit">Submit</button>
          </div>
        </fieldset>
      </form>
    </div>
  </div>
</div>
