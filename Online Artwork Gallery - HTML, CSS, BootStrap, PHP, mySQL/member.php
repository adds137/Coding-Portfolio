<?php 
	include("header.inc");
	include("nav.inc");
	$id = $_GET['username']; 
	$db = mysqli_connect("localhost","root","","artworks") or die(mysqli_error($db));
	$q ="SELECT * FROM artwork WHERE username = '$id'";
	$results = mysqli_query($db, $q)or die(mysqli_error($db));
	while($row=mysqli_fetch_array($results))
	{
		$filename = $row['filename'];
		 print "<a href='artwork.php?artwork_id={$row['artwork_id']}'><img class = 'img-thumbnail' src = $filename height = '200' width = '200'></a>";
	}
	include("footer.inc");
?>