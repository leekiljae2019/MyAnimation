# MyAnimation

1. 적용 기술
  - Koin
  - MVVM
  - ViewPager2
  - ROOM
  - Paging lib
  - Retrofit

2. 화면 동작 설명
  - 실행 시 이미지 리스트(더보기 가능)가 보이고 ViewPager2를 통해 좌우로 플리킹됨.
  - 리스트에서 이미지 선택 시 상세화면으로 이동하고 ViewPager2를 통해 좌우로 플리킹됨.
  
3. ROOM & Paging lib 
  이미지 리스트에 ROOM과 Paging lib를 적용 했는데 조금 억지스러운 부분이 있음.
  앱 실행 시 네트워크로 이미지를 받아와서 DB에 저장하고 앱 종료 시(& 실행 시) DB를 삭제함.
  이렇게 처리한 이유는 원래 해당 앱은 API 구조 및 동작이 ROOM을 적용하기에는 부적합한데 적용하는데 의의를 두다보니... ㅠ_ㅠ
  
 4. Animation이 적용된 부분
   - MainActivity의 ViewPager2 setPageTransformer() 함수
   - DetailActivity의 ViewPager2 setPageTransformer() 함수
   - 이미지 리스트에서 상세화면으로 이동할 때 Animation 처리 (MyPagingAdapter.kt)
