 <?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "app";

$name = $_GET['username'];
$address = $_GET['address'];
$email = $_GET['email'];
$mobile_number = $_GET['mobile_number'];
$pass = $_GET['password'];


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "INSERT INTO person (username, address, email, mobile_number, password)
VALUES ('$name', '$address', '$email', '$mobile_number', '$pass')";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?> 


