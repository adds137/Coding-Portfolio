<?php
	$username = $_POST['username'];
	$password = $_POST['password'];
	$db = mysqli_connect("localhost", "root", "", "artworks") or die(mysqli_error($db));
	$q = "select * from member where username = '$username' and password = sha('$password')";
	$result = mysqli_query($db, $q);
	if(mysqli_num_rows($result) > 0)
		{
			session_start();
			$_SESSION['username'] = $username;
			include("header.inc");
			print "<div class=row>";
?>
			<div class="col-lg-12 col-md-12">
<?php		
			include("nav.inc");
			print "<h2>You have logged in</h2>";
			print "</div>";
			print "<br>";
			include("footer.inc");
		}
	else
		{
			include("header.inc");
			print "<div class=row>";
?>
			<div class="col-lg-12 col-md-12">
<?php
			print "<br>";
			print "<h2>Either you are not registered or your username or password is wrong!!!</h2>"; 
			print " <a href= register.php> Click here to register </a>";
			print "<br>";
			print " <a href= login.php> Click here to Login again </a>";
			print "</div>";
			print "<br>";
			include("footer.inc");
		}
?>
			