#include <vector>

class STN{
private:
  std::vector<std::vector<int>> successorNodeMatrix;
  std::vector<std::vector<int>> edgeWeightMatrix;
  std::vector<int> numSuccessorsByNode;

  int nodeCount;

public:
  STN();
  std::vector<int> getSuccessorVector(int nodeID);
  std::vector<int> getEdgeWeightVector(int nodeID);

  int getSuccessor(int nodeID, int successorIndex);
  int getEdgeWeight(int nodeID, int successorIndex);
  int getNumSuccessors(int nodeID);

  void addNode(); // return nodeID?
  void connectNodes(int nodeFrom, int nodeTo, int edgeWeight);
  void deleteNode(int nodeID);
  void deleteNodeAndConnectSuccessors(int nodeID);



}
