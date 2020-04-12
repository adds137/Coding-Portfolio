<?php 
	include("header.inc");
	include("nav.inc");
	$id = $_GET['id']; 
	$db = mysqli_connect("localhost","root","","artworks") or die(mysqli_error($db));
	$q ="SELECT * FROM artwork WHERE artwork_id = '$id'";
	$results = mysqli_query($db, $q)or die(mysqli_error($db));
	while($row=mysqli_fetch_array($results))
	{
		print "<h2>{$row['title']}</h2>";
		print "<h3>by {$row['username']}<\h3>";
		print "<p>{$rwo['category']}</p>";
		print "<img src="{$row['filename']}">";
		print "<p>{$rwo['description']}</p>";
		print "<a href='member_artwork.php?name={$row['username']}'>{$row['username']}</a>";
	}
	include("footer.inc");
?>
		