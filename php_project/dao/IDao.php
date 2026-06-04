<?php
interface IDao {
    public function create($o);
    public function delete($id);
    public function update($o);
    public function findAll();
    public function findById($id);
}
?>
