<?php
	session_start();
	if(!isset($_SESSION['username']))
		{
			header("Location:login.php");
			exit(0);
		}
	$wb = "Welcome Back";
	include("header.inc");
	print  $wb . " " . $_SESSION['username'];
	print "<br>";
	print "<br>";
	include("nav.inc");
?>
<h2>Add new Artwork</h2>
<br>
<form action="process_add.php" method="post" enctype="multipart/form-data">
	Title: <input type="text" name="title" /><br />
	Category: <input type="text" name="category" /><br />
	<br>
	Description: <textarea rows=10 cols=50 name="description"></textarea><br>
	Tags:(separate with comma) <input type="text" name="tags" /><br />
	<br>
	Artwork: <input type="file" name = "artwork" />
	<br>
	<input type=submit value="Add New Artwork">
</form>
<br>
<?php
	include("footer.inc");
?>