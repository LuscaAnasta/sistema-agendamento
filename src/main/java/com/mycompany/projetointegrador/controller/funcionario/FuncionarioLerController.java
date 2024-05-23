/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetointegrador.controller.funcionario;

import com.mycompany.projetointegrador.Conexao;
import com.mycompany.projetointegrador.model.FuncionarioModel;
import com.mycompany.projetointegrador.model.FuncionarioTabela;
import com.mycompany.projetointegrador.view.funcionario.TelaFuncionarioCadastro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;

/**
 *
 * @author lucas.amsantos4
 */
public class FuncionarioLerController {

    private final TelaFuncionarioCadastro telaFuncionarioCadastro;
    
    public FuncionarioLerController(TelaFuncionarioCadastro telaFuncionarioCadastro){
        this.telaFuncionarioCadastro = telaFuncionarioCadastro;
    }
    public static FuncionarioTabela lerFuncionarioModel(){
        Conexao banco = new Conexao();
        banco.AbrirConexao();
    
    try {
        PreparedStatement qLinhas = banco.con.prepareStatement("SELECT COUNT(id_funcionario) AS total FROM tb_funcionario");
        ResultSet linhas = qLinhas.executeQuery();
        linhas.next(); // Mova o cursor para a primeira linha
        int nLinhas = linhas.getInt(1); // Acesse a primeira coluna pela posição do índice
        
        PreparedStatement lerDados = banco.con.prepareStatement("SELECT * FROM tb_funcionario");
        ResultSet resultado = lerDados.executeQuery();
        ResultSetMetaData remd = resultado.getMetaData();
        
        int colunaN = remd.getColumnCount();
        String[] nomeColuna = new String[colunaN];
        for(int c = 0; c < colunaN; c++) {
                nomeColuna[c] = remd.getColumnName(c + 1);
        }
        
        String[][] dados = new String[nLinhas][colunaN];
        int i = 0;
        while(resultado.next()) {
            for(int j = 0; j < colunaN; j++) {
               
                    dados[i][j] = resultado.getString(j + 1);
                
            }
            i++;
        }
        
        return new FuncionarioTabela(nomeColuna, dados);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Algo deu Errado.");
            System.out.println("Algo deu Errado\n  "+ex);
            System.err.println(ex);
        }finally{
            banco.FecharConexao();
        }
        return null;
        
    }
    
    
}
