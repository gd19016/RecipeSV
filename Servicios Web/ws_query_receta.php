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
$conexion=mysql_connect($servidor,$usuario,$password) or
die ("Problemas en la conexion");
mysql_select_db($baseDatos,$conexion)
or die("Problemas en la seleccion de la base de datos");
$registros=mysql_query("SELECT * FROM receta WHERE idReceta='".$idReceta."' AND nombre='".$nombre."' AND descripcion='".$descripcion."' AND pasos='".$pasos."' AND dificultad='".$dificultad."' AND tipo='".$tipo."' AND tiempo='".$tiempo."'",$conexion) or
die("Problemas en el select:".mysql_error());
$filas=array();
while ($reg=mysql_fetch_assoc($registros))
{
$filas[]=$reg;
}
echo json_encode($filas);
mysql_close($conexion);
?>