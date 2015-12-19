package pkg;

public class Servico {
	private String nomeservico;
	private String ip;
	private int porta;
	private int indicador;
	
	public Servico strToService(String strServico){
		Servico servico = new Servico();
		servico.setNomeservico(strServico.substring(0, strServico.indexOf(";")));
		servico.setIp(strServico.substring(strServico.indexOf(";")+1,strServico.lastIndexOf(";")));
		servico.setPorta(Integer.parseInt(strServico.substring(strServico.lastIndexOf(";")+1, strServico.length())));
		
		return servico;
	}
	
	public String getNomeservico() {
		return nomeservico;
	}
	public void setNomeservico(String servico) {
		this.nomeservico = servico;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
	public int getIndicador() {
		return indicador;
	}
	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}	
}
