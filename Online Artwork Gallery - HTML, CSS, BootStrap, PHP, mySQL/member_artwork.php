<?php 
	include("header.inc");
	include("nav.inc");
	$id = $_GET['name']; 
	$db = mysqli_connect("localhost","root","","artworks") or die(mysqli_error($db));
	$q ="SELECT * FROM artwork WHERE username = '$name'";
	$results = mysqli_query($db, $q)or die(mysqli_error($db));
	while($row=mysqli_fetch_array($results))
	{
		print "<a href='picture_details.php?id={$row['artwork_id']}'><img src="{$row['filename']}" width="200" "height="200"></a>";
	}
	include("footer.inc");
?>