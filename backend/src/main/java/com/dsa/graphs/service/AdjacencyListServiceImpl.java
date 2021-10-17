package com.dsa.graphs.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsa.graphs.models.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AdjacencyListServiceImpl implements AdjacencyListService {
    private static final Logger LOGGER = LogManager.getLogger(AdjacencyListServiceImpl.class);
    private Map<String, List<String>> adjacencyList = new HashMap<>();

    private NodeService nodeService;

    public AdjacencyListServiceImpl(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public void createAdjacencyList() {
        Set<User> nodes = nodeService.getNodes();
        System.out.println(nodes.size());
        LocalDateTime start = LocalDateTime.now();

        createNodes(nodes);
        createEdges(nodes);

        LocalDateTime end = LocalDateTime.now();

        System.out.println(adjacencyList.size());
    }

    public void createNodes(Set<User> nodes) {
        for (User user : nodes) {
            String friends = user.getFriends();

            if (friends != null) {
                String[] listOfFriends = user.getFriends().split(",");

                for (String friendID : listOfFriends) {
                    addNode(friendID);
                }
            }
            addNode(user.getUser_id());
        }
    }

    @Override
    public void createEdges(Set<User> nodes) {
        for (User user : nodes) {
            String friends = user.getFriends();

            if (friends != null) {
                String[] listOfFriends = user.getFriends().split(",");

                for (String friendID : listOfFriends) {
                    addEdge(user.getUser_id(), friendID);
                }
            }
        }
    }

    @Override
    public void addEdge(String fromUser, String toUser) {
        adjacencyList.get(fromUser).add(toUser);
        adjacencyList.get(toUser).add(fromUser);
    }

    @Override
    public void addNode(String userID) {
        adjacencyList.put(userID, new ArrayList<String>());
    }
}
