package it.unicam.ids.c3.interflocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.luogo.LockerService;

@Service
public class InterfLockerService {

	@Autowired
	LockerService lockerSer;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	InterfLockerRepository interfRep;
	
	/**
	 * Crea una nuova Interfaccia Locker, controllando che i parametri inseriti siano corretti
	 * e che non ci sia già un profilo legato al Locker
	 * @param interf
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaInterfLocker(InterfLocker interf) throws Exception {
		interf.setPassword(passwordEncoder.encode(interf.getPassword()));
		Long idLocker = interf.getIdLocker();
		if(idLocker != null && lockerSer.isLocker(idLocker)) {
			lockerSer.setInterfaccia(idLocker);
			interfRep.save(new InterfLocker(interf));
		} else throw new Exception("Id non legato ad IdLocker");
	}
	
	public Long apriCella(Long idInterf, String password) throws Exception {
		InterfLocker interf = interfRep.findById(idInterf).orElseThrow();
		return lockerSer.apriCella(interf.getIdLocker(), password);
	}

	public void eliminaInterfaccia(Long idLocker) {
		InterfLocker interf = interfRep.findByIdLocker(idLocker);
		if(interf != null)
			interfRep.deleteById(interf.getId());
	}
}
