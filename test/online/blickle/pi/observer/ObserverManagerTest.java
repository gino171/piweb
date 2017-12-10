package online.blickle.pi.observer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import online.blickle.pi.PortDescription;
import online.blickle.pi.PortDescriptionList;

import org.junit.Before;
import org.junit.Test;

public class ObserverManagerTest {

	private static String PORT_A = "PORT_A";
	
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void po_addObserver_ObserverCanBeFoundById() {
		ObserverManager om = new ObserverManager(getDummyPortDescriptionList());
		PortObserver p1 = new PortObserver(PORT_A,"DummyURL");
		assertTrue("PortObserverIs should be null", p1.getObserverId() == null);
		String id1 = om.addObserver(p1);
		assertTrue("PortObserverIs should be set (!=null)",om.getObserver(id1) != null);
	}

	@Test
	public void po_searchObserverByID_ObserverIsFound() {
		ObserverManager om = new ObserverManager(getDummyPortDescriptionList());
		
		PortObserver p1 = new PortObserver(PORT_A,"DummyURL1");
		String id1 = om.addObserver(p1);
		
		PortObserver p2 = new PortObserver(PORT_A,"DummyURL2");
		String id2 =om.addObserver(p2);
		
		assertTrue("PortObserver" +p1.getObserverId()+" should be found", om.getObserver(id1) == p1);
		assertTrue("PortObserver" +p2.getObserverId()+" should be found", om.getObserver(id2) == p2);
		assertFalse("Observer should be differnt",p1.equals(p2));
	
	}
	
	@Test
	public void po_addObserverForSamePortAndURL_ObserverNotAddedButExistingReturned() {
		ObserverManager om = new ObserverManager(getDummyPortDescriptionList());
		
		PortObserver p1 = new PortObserver(PORT_A,"SameURL");
		String id1 = om.addObserver(p1);
		
		PortObserver p2 = new PortObserver(PORT_A,"SameURL");
		String id2 = om.addObserver(p2);
		
		assertTrue(id1.equals(id2));
		assertTrue(om.getObserver(id1)==om.getObserver(id2));
		assertTrue(om.getAllObservers().size()==1);
		
	
	}
	
	@Test
	public void po_removeObserver_observerNotKnown() {
		ObserverManager om = new ObserverManager(getDummyPortDescriptionList());
		PortObserver p1 = new PortObserver(PORT_A,"DummyURL1");
		String id1 = om.addObserver(p1);
		
		assertTrue("PortObserver" +p1.getObserverId()+" should be found", om.getObserver(id1) == p1);
		
		om.removeObserver(id1);
		assertTrue("PortObserverList should be empty", om.getAllObservers().size()==0);
		
	}

	@Test
	public void po_createPortObeserverForUnkonwnPort_IllegalArgumetExceptionIsThrown() {
		ObserverManager om = new ObserverManager(getDummyPortDescriptionList());
		try {
			PortObserver p1 = new PortObserver("UNKNOWN_PORT","DummyURL1");
			om.addObserver(p1);
			assertFalse(true);
		} catch (IllegalArgumentException ex) {
			assertTrue("Expected Exception for UNKNOWN_PORT",true);
		}
		
	}
	private ObserverManager createObserverManagerWithManyObservers(int numberOfObservers) {
		ObserverManager om = new ObserverManager(getDummyPortDescriptionList());
		for (int i=0; i<numberOfObservers; i++) {
			PortObserver p1 = new PortObserver(PORT_A,"DummyURL_"+i);
			om.addObserver(p1);
		}
		return om;
	}


	private PortDescriptionList getDummyPortDescriptionList() {
		
		PortDescription p1 = new PortDescription(0,PORT_A,false,"Dummy Port 1");
		ArrayList<PortDescription> pA = new ArrayList<>();
		pA.add(p1);
		PortDescriptionList pL = new PortDescriptionList(pA);
		return pL;
	}
}
