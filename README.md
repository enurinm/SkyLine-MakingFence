# SkyLine-MakingFence
Use the tree structure to get the skyline.

```
- 이 프로그램은 다양한 높이와 길이의 나무판자들이 랜덤한 위치를 가지고 세워져 있을 때 스카이라인이 되는 최소한의 판자들을 골라내는 프로그램입니다.
  - This program picks out the minimum number of boards that become skyline when wooden boards of varying heights and lengths are placed in random locations.

- 사용자로부터 나무판자의 개수와 각 나무판자들의 정보(원점으로부터 떨어져 있는 거리, 판자의 너비, 판자의 높이)를 입력받습니다.
  - You will receive information from the user on the number of wooden boards and information from each of the wooden boards (distance from origin, width of board, height of board).
  
- 남아있는 나무판의 개수(스카이라인을 구성하는 최소한의 나무판자)와 남아있는 나무판자의 이름이 출력됩니다.
  - The number of remaining wooden boards (minimum number of wooden boards that make up the case) and the name of the remaining wooden boards are printed.
```

#### Example Input
6 *//the total number of wooden boards*  
15 8 4 *//wooden boards 1*  
10 8 3 *//wooden boards 2*  
25 3 7 *//wooden boards 3*  
3 9 5 *//wooden boards 4*  
1 5 3 *//wooden boards 5*  
7 2 2 *//wooden boards 6*  

#### Example Output
5  
1 2 3 4 5
