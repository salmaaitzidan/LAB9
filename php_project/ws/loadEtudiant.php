<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');

include_once '../service/EtudiantService.php';
$es = new EtudiantService();
echo json_encode($es->findAllApi());
?>
