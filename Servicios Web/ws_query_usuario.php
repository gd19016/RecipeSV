<?php
$email=$_REQUEST['email'];
$password=$_REQUEST['password'];
$nombre=$_REQUEST['nombre'];
$idRol=$_REQUEST['idRol'];
$servidor="localhost";
$usuario="id19079472_admin";
$password="fg\]*S0%t0W8oeB!";
$baseDatos="id19079472_recipesv";
$conexion=mysql_connect($servidor,$usuario,$password) or
die ("Problemas en la conexion");
mysql_select_db($baseDatos,$conexion)
or die("Problemas en la seleccion de la base de datos");
$registros=mysql_query("SELECT * FROM usuario WHERE email='".$email."' AND password='".$password."' AND nombre='".$nombre."' AND idRol='".$idRol."'",$conexion) or
die("Problemas en el select:".mysql_error());
$filas=array();
while ($reg=mysql_fetch_assoc($registros))
{
$filas[]=$reg;
}
echo json_encode($filas);
mysql_close($conexion);
?>