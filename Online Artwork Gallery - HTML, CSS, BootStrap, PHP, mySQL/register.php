<?php
	include("header.inc");
?>
<div class="col-lg-12 col-md-12">
<?php
	include("nav.inc");
?>
<h2>Register:</h2>
<form action="process_user.php" method="post">
	Username: <input type="text" name="username" /><br /><br>
	Password: <input type="password" name="password" /><br /><br>
	<input type="submit" value="Register" />
</form>
<?php
	include("footer.inc");
?>
</div>