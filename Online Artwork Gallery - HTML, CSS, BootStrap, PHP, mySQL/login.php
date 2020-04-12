<?php
	include("header.inc");
?>
<div class="col-lg-12 col-md-12">
<?php
	include("nav.inc");
?>
<h2>Login:</h2>
<form action="process_login.php" method="post">
	Username: <input type="text" name="username" /><br /><br>
	Password: <input type="password" name="password" /><br /><br>
	<input type="submit" value="Login" />
</form>
<?php
	include("footer.inc");
?>
</div>