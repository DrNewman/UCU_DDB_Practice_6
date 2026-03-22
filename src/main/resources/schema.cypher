// src/main/resources/schema.cypher
MATCH (n) DETACH DELETE n;
CREATE (i1:Item {name: "Laptop", price: 1000, likes: 0}),
       (i2:Item {name: "Mouse", price: 20, likes: 0}),
       (i3:Item {name: "Monitor", price: 300, likes: 0}),
       (i4:Item {name: "Keyboard", price: 50, likes: 0});
CREATE (c1:Customer {name: "Oleksandr"}), (c2:Customer {name: "Ivan"});
CREATE (o1:Order {id: "ORD-001", date: "2026-03-22"}),
       (o2:Order {id: "ORD-002", date: "2026-03-23"}),
       (o3:Order {id: "ORD-003", date: "2026-03-24"}),
       (o4:Order {id: "ORD-004", date: "2026-03-25"});
MATCH (c1:Customer {name: "Oleksandr"}), (c2:Customer {name: "Ivan"}),
      (i3:Item {name: "Monitor"}), (i4:Item {name: "Keyboard"})
CREATE (c1)-[:VIEWED]->(i3), (c1)-[:VIEWED]->(i4), (c2)-[:VIEWED]->(i4);
MATCH (c1:Customer {name: "Oleksandr"}), (c2:Customer {name: "Ivan"}),
      (o1:Order {id: "ORD-001"}), (o2:Order {id: "ORD-002"}),
      (o3:Order {id: "ORD-003"}), (o4:Order {id: "ORD-004"})
CREATE (c1)-[:PLACED]->(o1), (c1)-[:PLACED]->(o2), (c1)-[:PLACED]->(o4), (c2)-[:PLACED]->(o3);
MATCH (o1:Order {id: "ORD-001"}), (o2:Order {id: "ORD-002"}),
      (o3:Order {id: "ORD-003"}), (o4:Order {id: "ORD-004"}),
      (i1:Item {name: "Laptop"}), (i2:Item {name: "Mouse"}),
      (i3:Item {name: "Monitor"}), (i4:Item {name: "Keyboard"})
CREATE (o1)-[:CONTAINS]->(i1), (o1)-[:CONTAINS]->(i2),
       (o2)-[:CONTAINS]->(i2), (o2)-[:CONTAINS]->(i4),
       (o3)-[:CONTAINS]->(i2), (o3)-[:CONTAINS]->(i3),
       (o4)-[:CONTAINS]->(i3);