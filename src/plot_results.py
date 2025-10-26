import json
import matplotlib.pyplot as plt
import numpy as np

data = json.load(open(r"C:\Users\Владелец\IdeaProjects\Java3\src\main\resources\output\ass_3_output.json", encoding="utf-8"))

graphs = np.arange(len(data))
prim_time = [g["Prim_TimeMs"] for g in data]
kruskal_time = [g["Kruskal_TimeMs"] for g in data]

plt.bar(graphs - 0.2, prim_time, width=0.4, label="Prim Time (ms)", color="#6baed6")
plt.bar(graphs + 0.2, kruskal_time, width=0.4, label="Kruskal Time (ms)", color="#fd8d3c")

plt.xticks(graphs, [g["Graph"] for g in data])
plt.xlabel("Graph ID")
plt.ylabel("Execution Time (ms)")
plt.title("Prim vs Kruskal Execution Time Comparison")
plt.legend()
plt.show()
