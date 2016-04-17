package Model;

import java.awt.Point;

public interface ISearchDomain {
	Node getInitialNode(Point initialLocation);
	boolean isTarget(Node node);
	void foreachSuccessor(Node current, SuccessorLogic successorLogic);
}
