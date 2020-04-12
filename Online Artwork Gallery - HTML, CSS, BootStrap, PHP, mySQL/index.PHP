<?php
	include("header.inc");;
?>
<div class="container" height = '600px' width = '600px'>
	<div class=row>
		<div class="col-lg-6 col-md-6">
			<div id="myCarousel" class="carousel slide" data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="myCarousel" data-side-to="0" class="active"></li>
					<li data-target="myCarousel" data-side-to="1"></li>
					<li data-target="myCarousel" data-side-to="2"></li>
					<li data-target="myCarousel" data-side-to="3"></li>
				</ol>
				<div class="carousel-inner" role="listbox" id = "carousel">
<?php
				$db = mysqli_connect("localhost", "root", "","artworks") or die(mysqli_error($db));
				$q = "select filename from artwork order by artwork_id desc limit 4";
				$results = mysqli_query($db, $q) or die(mysqli_error($db));
				$arrayrow = mysqli_fetch_array($results);
				print "<div class = 'item active carousel'>";
				print "<img src = '{$arrayrow['filename']}' height = '600px' width = '600px'>";
				print "</div>";
				while($row = mysqli_fetch_array($results))
				{
					print "<div class = 'item carousel'>";
					print "<img src = '{$row['filename']}' height = '600px' width = '600px'>";
					print "</div>";
				}
?>
				</div>
				<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
		<div class="col-lg-6 col-md-6">
			<h4>"Every child is an artist. The problem is how to remain an artist once we grow up" - Pablo Picasso</h4>
		</div>
	</div>
<div>
<?php
	include("nav.inc");
?>
<div class="container-fluid">
	<div class=row>
		<div class="col-lg-4 col-md-4">
			<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla fermentum augue a ligula lacinia, id fermentum sem laoreet. Nullam ullamcorper congue pellentesque. Phasellus tincidunt faucibus vulputate. Nunc consectetur, turpis quis vehicula rutrum, lectus neque fermentum quam, sit amet pharetra est nulla porttitor enim. Vestibulum id interdum ligula. Nulla metus ex, tempor ac vehicula eget, hendrerit vel ante. Pellentesque iaculis vel lorem hendrerit commodo. Donec a efficitur augue. Aliquam sollicitudin orci at massa imperdiet, eu hendrerit urna maximus. Pellentesque pulvinar finibus risus, hendrerit laoreet ipsum posuere quis. Curabitur finibus vehicula blandit. Vivamus vel ipsum sit amet sapien congue volutpat. Vestibulum iaculis convallis blandit.</p>
		</div>
		<div class="col-lg-4 col-md-4">
			<p>Sed sed arcu eu ante congue dignissim quis bibendum erat. Sed rutrum odio nec venenatis imperdiet. Suspendisse rhoncus condimentum faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. In non nulla id dui sagittis convallis sit amet eu arcu. Nulla consequat arcu sit amet condimentum cursus. Nunc et elit massa. Donec accumsan auctor convallis. Phasellus vitae rutrum magna. Morbi viverra luctus enim, id pharetra mi commodo a.</p>
		</div>
		<div class="col-lg-4 col-md-4">
			<p>Mauris rutrum lacus in neque viverra pulvinar. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec convallis consectetur ante sit amet suscipit. Vestibulum nec ipsum vehicula, maximus urna condimentum, condimentum est. Etiam fermentum mauris magna, a placerat mi luctus sit amet. Suspendisse euismod nisi quis lectus pharetra, id gravida arcu dignissim. Sed vel nisi facilisis, feugiat elit vehicula, sollicitudin turpis. Ut tincidunt tortor ac mauris mattis, non ultricies magna varius. Etiam commodo tristique iaculis. Morbi risus augue, eleifend eu pharetra ac, eleifend at ante.</p>
		</div>
	</div>
</div>
<?php
                $db = mysqli_connect('localhost', 'root', '', 'artworks') or die(mysqli_error($db));
				$q = "SELECT DISTINCT username FROM artwork";
				$results = mysqli_query($db, $q) or die(mysqli_error($db));
				while($row = mysqli_fetch_array($results))
                {
                    print "<a href='member.php?username={$row['username']}'>{$row['username']}</a>";
                }
	include("footer.inc");
?>