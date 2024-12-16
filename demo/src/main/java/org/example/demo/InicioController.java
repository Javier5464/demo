package org.example.demo;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.example.demo.modelo.bd.PeliculaDAO;
import org.example.demo.modelo.bd.UsuarioDAO;
import org.example.demo.modelo.clases.Pelicula;

import java.util.List;

public class InicioController {

    @FXML
    private TextField idField;

    @FXML
    private TextField tituloField;

    @FXML
    private TextField generoField;

    @FXML
    private TableView<Pelicula> peliculasTableView;

    @FXML
    private ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

    private PeliculaDAO peliculaDAO;
    private UsuarioDAO usuarioDAO;
    private int userid;


    public InicioController() {
        this.userid = 0;
        peliculaDAO = new PeliculaDAO();
        usuarioDAO = new UsuarioDAO();
        peliculas = FXCollections.observableArrayList();
    }


    public InicioController(int userid) {
        this.userid = userid;
        peliculaDAO = new PeliculaDAO();
        usuarioDAO = new UsuarioDAO();
        peliculas = FXCollections.observableArrayList();
    }

    @FXML
    private TableColumn<Pelicula, Boolean> favoritaColumn;

    @FXML
    private TableColumn<Pelicula, String> tituloColumn;

    @FXML
    private TableColumn<Pelicula, String> generoColumn;

    @FXML
    public void initialize() {
        // Configurar la columna "Favorita" con CheckBoxTableCell
        favoritaColumn.setCellValueFactory(cellData -> cellData.getValue().favoritaProperty());
        favoritaColumn.setCellFactory(CheckBoxTableCell.forTableColumn(favoritaColumn));

        // Configurar las columnas "Título" y "Género"
        tituloColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        generoColumn.setCellValueFactory(cellData -> cellData.getValue().generoProperty());

        // Vincular la ObservableList al TableView
        peliculasTableView.setItems(peliculas);

        // Cargar películas en la tabla
        cargarPeliculas();
    }


    private void cargarPeliculas() {
        peliculas.clear();
        List<Pelicula> peliculasBD = peliculaDAO.getBDPeliculas();

        for (Pelicula pelicula : peliculasBD) {
            if (usuarioDAO.isFavorita(userid, pelicula.getIdPelicula())) {
                pelicula.setFavorita(true);
            }
            peliculas.add(pelicula);
        }

        peliculasTableView.refresh();
    }




    @FXML
    public void agregarPelicula() {
        String titulo = tituloField.getText();
        String genero = generoField.getText();

        Pelicula pelicula = new Pelicula(0, titulo, genero);
        if (peliculaDAO.insertarPelicula(pelicula)) {

            cargarPeliculas();
            limpiarCampos();
        }
    }


    @FXML
    public void buscarPelicula() {
        String nombre = tituloField.getText();

        try {
            List<Pelicula> peliculas = peliculaDAO.getBDPeliculasByName(nombre);
            if (!peliculas.isEmpty()) {
                peliculasTableView.getItems().clear();
                peliculasTableView.getItems().addAll(peliculas);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Película no encontrada");
                alert.setContentText("No se encontró ninguna película con el nombre proporcionado.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al buscar la película");
            alert.setContentText("Hubo un error al buscar la película.");
            alert.showAndWait();
        }
    }

    @FXML
    public void guardarFavoritos() {
        for (Pelicula pelicula : peliculas) {
            if (usuarioDAO.isFavorita(userid, pelicula.getIdPelicula())) {
                usuarioDAO.agregarPeliculaFavorita(userid, pelicula.getIdPelicula());
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Favoritos guardados");
        alert.setContentText("Las películas favoritas han sido guardadas correctamente.");
        alert.showAndWait();
    }

    private void limpiarCampos() {
        idField.clear();
        tituloField.clear();
        generoField.clear();
    }
}
