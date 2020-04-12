<?php
	$username = $_POST['username'];
	$password = $_POST['password'];
	$db = mysqli_connect("localhost", "root", "", "artworks") or die(mysqli_error($db));
	$q = "insert into member values(null, '$username',SHA('$password'), now())";
	mysqli_query($db, $q) or die (mysqli_error($db));
	header("Location:index.php");
	exit(0);
?>