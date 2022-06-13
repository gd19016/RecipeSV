<?php
$idReceta=$_REQUEST['idReceta'];
$nombre=$_REQUEST['nombre'];
$descripcion=$_REQUEST['descripcion'];
$pasos=$_REQUEST['pasos'];
$dificultad=$_REQUEST['dificultad'];
$tipo=$_REQUEST['tipo'];
$tiempo=$_REQUEST['tiempo'];
$servidor="localhost";
$usuario="id19079472_admin";
$password="fg\]*S0%t0W8oeB!";
$baseDatos="id19079472_recipesv";
$respuesta=array('resultado'=>0);
json_encode($respuesta);
$conexion=mysql_connect($servidor,$usuario,$password) or
die ("Problemas en la conexion");
mysql_select_db($baseDatos,$conexion)
or die("Problemas en la seleccion de la base de datos");
$query = "DELETE FROM receta WHERE idReceta='".$idReceta."' AND nombre='".$nombre."' AND descripcion='".$descripcion."' AND pasos='".$pasos."' AND dificultad='".$dificultad."' AND tipo='".$tipo."' AND tiempo='".$tiempo."'";
echo($query);
$resultado = mysql_query($query) or die(mysql_error());
//Si la respuesta es correcta enviamos 1 y sino enviamos 0
if(mysql_affected_rows() == 1)
$respuesta=array('resultado'=>1);
echo json_encode($respuesta);
mysql_close($conexion);
?>