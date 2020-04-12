<?php
	session_start();
	session_destroy();
	include("header.inc");
	include("nav.inc");
?>
<h2>You have Successfully Logout</h2>
<?php
	include("footer.inc");
?>