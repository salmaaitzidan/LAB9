<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include_once '../service/EtudiantService.php';

    $id     = isset($_POST['id'])     ? intval($_POST['id'])       : 0;
    $nom    = isset($_POST['nom'])    ? trim($_POST['nom'])         : '';
    $prenom = isset($_POST['prenom']) ? trim($_POST['prenom'])      : '';
    $ville  = isset($_POST['ville'])  ? trim($_POST['ville'])       : '';
    $sexe   = isset($_POST['sexe'])   ? trim($_POST['sexe'])        : '';

    if (!$id || !$nom || !$prenom || !$ville || !$sexe) {
        http_response_code(400);
        echo json_encode(['error' => 'Données incomplètes.']);
        exit;
    }

    $es = new EtudiantService();
    $es->update(new Etudiant($id, $nom, $prenom, $ville, $sexe));
    echo json_encode($es->findAllApi());
} else {
    http_response_code(405);
    echo json_encode(['error' => 'Méthode non autorisée.']);
}
?>
