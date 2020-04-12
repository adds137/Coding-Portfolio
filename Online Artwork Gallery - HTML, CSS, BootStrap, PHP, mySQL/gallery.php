<?php
	session_start();
	include("header.inc");
	include("nav.inc");
?>
<form>
	<select id="dropdown" onchange="dropdown();">
		<option value"#">Category</option>
		<option value"gallery.php">Any</option>
<?php
if (isset($_GET['id']))
{ 
	$id = $_GET['id'];
}
$db = mysqli_connect("localhost", "root","", "artworks")  or die(mysqli_error($db));
	$q = "select distinct category from artwork";
	$results = mysqli_query($db, $q) or die(mysqli_error($db));
	while($row=mysqli_fetch_array($results))
	{
		print "<option value='gallery.php?id={$row['category']}'>{$row['category']}</option>";
	}
?>
</select>
<?php
if(isset($id))
{
	$q2 = "select * from artwork where category = '$id'";
	print "<h2>$id</h2>";
}
else
{
	$q2 = "select * from artwork";
	print "<h2>All Images</h2>";
}
$results = mysqli_query($db, $q2) or die(mysqli_error($db));
if (mysqli_num_rows($results) > 0)
{
	while ($row = mysqli_fetch_array($results))
	{
		$filename = $row['filename'];
        print "<a href='artwork.php?artwork_id={$row['artwork_id']}'><img class = 'img-thumbnail' src = $filename height = '200' width = '200'></a>";
	}
	include("footer.inc");
}
?>