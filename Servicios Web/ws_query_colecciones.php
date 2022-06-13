<?php
$idColeccion=$_REQUEST['idColeccion'];
$nombre=$_REQUEST['nombre'];
$descripcion=$_REQUEST['descripcion'];
$idUsuario=$_REQUEST['idUsuario'];
$servidor="localhost";
$usuario="id19079472_admin";
$password="fg\]*S0%t0W8oeB!";
$baseDatos="id19079472_recipesv";
$conexion=mysql_connect($servidor,$usuario,$password) or
die ("Problemas en la conexion");
mysql_select_db($baseDatos,$conexion)
or die("Problemas en la seleccion de la base de datos");
$registros=mysql_query("SELECT * FROM colecciones WHERE idColeccion='".$idColeccion."' AND nombre='".$nombre."' AND descripcion='".$descripcion."' AND idUsuario='".$idUsuario."'",$conexion) or
die("Problemas en el select:".mysql_error());
$filas=array();
while ($reg=mysql_fetch_assoc($registros))
{
$filas[]=$reg;
}
echo json_encode($filas);
mysql_close($conexion);
?>