gremlin测试语句：

查找：
g.V().has("name","刘备").outE().as("e").inV().has("name","张飞").select("e")
g.V().has("name","刘备").outE()

更新：
g.V(4192)v.property(Cardinality.single, 'name', 'william').next()
