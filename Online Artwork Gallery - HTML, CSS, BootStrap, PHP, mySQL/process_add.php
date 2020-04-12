<?php
	session_start();
	include("header.inc");
	include("nav.inc");
	$db = mysqli_connect("localhost", "root", "", "artworks")or die(mysqli_error($db));
	$user = $_SESSION['username'];
	$title = $_POST['title'];
	$category = $_POST['category'];
	$description = $_POST['description'];
	$tags = $_POST['tags'];
	$tmp = $_FILES["artwork"]["tmp_name"];
	$dest = "uploads/{$_FILES["artwork"]["name"]}";
	move_uploaded_file($tmp, $dest);
	$q = "insert into artwork values(null, '$user', '$title', '$category', '$description', '$tags', '$dest')";
	mysqli_query($db, $q);
	print ("New artwork added successfully");
	include("footer.inc");
?>