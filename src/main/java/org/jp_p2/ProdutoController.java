package org.jp_p2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.model.Produto;
import org.dao.ProdutoDAO; // IMPORTANTE: Mudar a importação para o DAO

import java.util.List;

public class ProdutoController {

    // 1. Injeção dos componentes FXML (fx:id)
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtQuantidade;

    @FXML private Button btnAdicionar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnExcluir;

    @FXML private TableView<Produto> tableViewProdutos;
    @FXML private TableColumn<Produto, Long> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colQuantidade;

    // Lista de dados para a TableView
    private ObservableList<Produto> produtosData = FXCollections.observableArrayList();

    // SUBSTITUIÇÃO: Camada de persistência (DAO)
    private ProdutoDAO produtoDAO = new ProdutoDAO(); // Instanciando o DAO diretamente

    /**
     * Método de inicialização chamado após o FXML ser carregado.
     */
    @FXML
    public void initialize() {
        // 2. Configuração das Colunas da TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Define a fonte de dados da TableView
        tableViewProdutos.setItems(produtosData);

        // 3. Carregar os dados iniciais do banco de dados
        carregarProdutos();

        // 4. Configurar listener para seleção de linha
        tableViewProdutos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> mostrarDetalhesProduto(newValue));
    }

    /**
     * Carrega a lista de produtos do DAO e atualiza a TableView.
     */
    private void carregarProdutos() {
        produtosData.clear();
        try {
            // AJUSTE: Usando produtoDAO.buscarTodos()
            List<Produto> produtos = produtoDAO.buscarTodos();
            if (produtos != null) {
                produtosData.addAll(produtos);
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Carga", "Erro ao carregar produtos do banco de dados: " + e.getMessage());
        }
    }

    /**
     * Preenche os campos de texto com os detalhes do produto selecionado.
     */
    private void mostrarDetalhesProduto(Produto produto) {
        if (produto != null) {
            txtId.setText(String.valueOf(produto.getId()));
            txtNome.setText(produto.getNome());
            txtPreco.setText(String.valueOf(produto.getPreco()));
            txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        } else {
            limparCampos();
        }
    }

    private void limparCampos() {
        txtId.setText("Gerado Automaticamente");
        txtNome.clear();
        txtPreco.clear();
        txtQuantidade.clear();
    }

    // --- MÉTODOS DE EVENTO (onAction) ---

    /**
     * Lógica para Adicionar um novo Produto.
     */
    @FXML
    private void handleAdicionarProduto() {
        try {
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            // O ID é null, pois o banco de dados deve gerar um novo
            Produto novoProduto = new Produto(null, nome, preco, quantidade);

            // AJUSTE: Usando produtoDAO.salvar()
            produtoDAO.salvar(novoProduto);

            carregarProdutos();
            limparCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto adicionado com sucesso!");
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Input", "Preço e Quantidade devem ser números válidos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível adicionar o produto: " + e.getMessage());
        }
    }

    /**
     * Lógica para Atualizar o Produto selecionado.
     */
    @FXML
    private void handleAtualizarProduto() {
        Produto produtoSelecionado = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try {
                // Coleta os dados dos campos (incluindo o ID)
                Long id = Long.parseLong(txtId.getText());
                String nome = txtNome.getText();
                double preco = Double.parseDouble(txtPreco.getText());
                int quantidade = Integer.parseInt(txtQuantidade.getText());

                Produto produtoAtualizado = new Produto(id, nome, preco, quantidade);

                // AJUSTE: Usando produtoDAO.atualizar()
                produtoDAO.atualizar(produtoAtualizado);

                carregarProdutos();
                limparCampos();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto atualizado com sucesso!");
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro de Input", "Preço, Quantidade e ID devem ser números válidos.");
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível atualizar o produto: " + e.getMessage());
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um produto na tabela para atualizar.");
        }
    }

    /**
     * Lógica para Excluir o Produto selecionado.
     */
    @FXML
    private void handleExcluirProduto() {
        Produto produtoSelecionado = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try {
                // AJUSTE: Usando produtoDAO.deletar()
                produtoDAO.deletar(produtoSelecionado.getId());

                carregarProdutos();
                limparCampos();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto excluído com sucesso!");
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível excluir o produto: " + e.getMessage());
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um produto na tabela para excluir.");
        }
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}