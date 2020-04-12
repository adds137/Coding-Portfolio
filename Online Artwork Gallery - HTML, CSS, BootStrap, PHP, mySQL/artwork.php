<?php 
	include("header.inc");
	include("nav.inc");
	$id = $_GET['artwork_id']; 
	$db = mysqli_connect("localhost","root","","artworks") or die(mysqli_error($db));
	$q ="SELECT * FROM artwork WHERE artwork_id = '$id'";
	$results = mysqli_query($db, $q)or die(mysqli_error($db));
	while($row=mysqli_fetch_array($results))
	{
		print "<h2>{$row['title']}</h2>";
		print "<h3>By: {$row['username']}</h3>";
		print "<p>{$row['category']}</p>";
		print "<img src='{$row['filename']}'>";
		print "<p>{$row['description']}</p>";
		print "<a href='member_artwork.php?name={$row['username']}'>{$row['username']}</a>";
	}
	include("footer.inc");
?>
		