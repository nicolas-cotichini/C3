package it.unicam.ids.c3.acquisti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class MerceService {

	@Autowired
	MerceRepository merceRep;

	/**
	 * Controlla se esiste già una Merce con questo nome e nel caso aggiurnge agli
	 * id Negozi associati anche quello corrente, altrimenti crea una nuova merce
	 * 
	 * @param merce
	 * @param idNegozio
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void creaMerce(Merce merce, Long idNegozio) throws Exception {
		Merce mrc = merceRep.findByNome(merce.getNome().toLowerCase());
		merce.setNome(merce.getNome().toLowerCase());
		if (mrc != null) {
			if (!mrc.getListaNegozi().contains(idNegozio)) {
				mrc.setListaNegozi(idNegozio);
				merceRep.save(mrc);
			} else
				throw new Exception("Merce: '" + merce.getNome() + "' già registrato per questo negozio");
		} else
			merceRep.save(new Merce(merce, idNegozio));

	}

	/**
	 * Ritorna la lista degli id Negozi che vendono la Merce cercata
	 * 
	 * @param nomeMerce
	 * @return List idNegozio che vendono la Merce
	 */
	public List<Long> cercaNegoziMerce(String nomeMerce) {
		Merce mrc = merceRep.findByNome(nomeMerce.toLowerCase());
		if (mrc != null) {
			return mrc.getListaNegozi();
		}
		return null;
	}

	/**
	 * Restituisce la lista delle merci vendute dal Negozio indicato
	 * 
	 * @param idNegozio
	 * @return List di Merce legate all'idNegozio, altrimenti null
	 */
	public List<Merce> getAllMerceByIdNegozio(Long idNegozio) {
		List<Merce> trovato = merceRep.findByListaNegozi(idNegozio);
		if (trovato.isEmpty())
			return null;
		return trovato;
	}

	/**
	 * Elimina l'id del negozio associata alla Merce, se la Merce non è associata a
	 * nessun Negozio allora viene eliminata la Merce stessa
	 * 
	 * @param idMerce
	 * @param nome
	 * @return boolean esito eliminazione
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public boolean eliminaMerce(String nome, Long idNegozio) {
		Merce mrc = merceRep.findByNome(nome);
		if (mrc != null) {
			if (mrc.getListaNegozi().contains(idNegozio)) {
				mrc.getListaNegozi().remove(idNegozio);
				if (mrc.getListaNegozi().isEmpty())
					merceRep.delete(mrc);
				else
					merceRep.save(mrc);
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Elimina tutte le merci associate a quel Negozio
	 * 
	 * @param id Negozio
	 */
	public void eliminaMerceByIdNegozio(Long idNegozio) {
		List<Merce> merci = merceRep.findByListaNegozi(idNegozio);
		if (!merci.isEmpty())
		for (Merce merce : merci) {
			eliminaMerce(merce.getNome(), idNegozio);
		}
	}

}
