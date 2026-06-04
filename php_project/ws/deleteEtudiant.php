<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include_once '../service/EtudiantService.php';

    $id = isset($_POST['id']) ? intval($_POST['id']) : 0;
    if (!$id) {
        http_response_code(400);
        echo json_encode(['error' => 'ID invalide.']);
        exit;
    }

    $es = new EtudiantService();
    $es->delete($id);
    echo json_encode($es->findAllApi());
} else {
    http_response_code(405);
    echo json_encode(['error' => 'Méthode non autorisée.']);
}
?>
