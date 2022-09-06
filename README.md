# wanted_preonboarding_android

해당 뉴스앱은 액티비티하나와 5개의 프래그먼트로 이루어져있습니다.

<br/>

## 메인액티비티의 경우

메인액티비티에서는 이 5개의 프래그먼트들을 enum class로 7가지 분류로 나누어 스택으로 각 프래그먼트들을 관리합니다. 각각의 프래그먼트를 이동할 때마다 스택을 add,pop,clear,peek으로 스택을 최신화해주거나 확인을 하여 toolBar의 title이나 home 키를 운용할 수 있습니다.

<br/>

## TopNewsFragment의 경우

기본적으로 처음에 불러옵니다. 처음 불러올 때를 제외하면 바텀네비게이션 바에서 TopNews를 누를 경우 기존에 불러왔던 topNewsFragment로 FragmentManager에 replace합니다.
뉴스 목록을 보여주는 프래그 먼트로서 SwipeRefreshLayout과 RecyclerView로 이루어 fragment_news_list를 뷰바인딩하여 만들어집니다.
Retrofit과gson,okhttp3를 이용하여 NewsApi에서 Rest방식으로 데이터를 가져옵니다. 가져온 데이터의 경우 리사이클러뷰의 어뎁터인 NewsAdapter에 데이터들을 넣어줍니다. 그데이터중 imageUrl을 이미지로딩라이브러리인 Glide를 이용하여 이미지를 가져옵니다.
해당 데이터를 클릭할 경우 onClickListener를 통하여 해당 데이터를 매개변수로 전달하여 MainActivity의 showDetailFragment를 호출하여 NewsDetailFragment를 FragmentManager에 add할수있습니다.
뉴스 내용을 최신화하기 위해서는 RecyclerView를 당길 경우 최신화가 됩니다.

<br/>

## CategoryFragment의 경우

바텀네비게이션 바에서 Category를 누를 경우 현재 프래그먼트를 categoryFragment로 replace 합니다.
단순히 카테고리를 선택하는 프래그먼트로서 fragment_category를 뷰바인딩하여 만들어집니다.
카테고리를 선택할 경우 해당 index를 매개변수로 MainActivity의 showCategoryListFragment를 호출하여 CategoryListFragment를 FragmentManager에 add 할 수 있습니다.

<br/>

## CategoryListFragment의 경우

MainActivity의 showCategoryListFragment를 제외하고는 다른 함수로는 나오지 않습니다.
카테고리별 뉴스 목록을 보여주는 프래그먼트로서 fragment_news_list를 뷰바인딩하여 만들어집니다.
전체적 인내용은 TopNewsFragment와 내용은 같습니다. 차이점이라고 한다면 매개변수로 받아온 인덱스를 이용하여 카테고리별로 데이터를 가지고 온다는 점입니다.

<br/>

## SaveNewsFragment의 경우

바텀네비게이션 바에서 Save를 누를 경우 현재 프래그먼트를 saveFragment로 replace 합니다.
저장한 뉴스들의 목록을 보여주는 프래그먼트로서 fragment_save_news_list를 뷰바인딩하여 만들어집니다.
RoomDB를 이용하여 목록을 가져온다는 것과 당겨도 최신화가 되지 않는다는 것을 제외하면 TopNewsFragment와 내용은 같습니다.

<br/>

## NewsDetailFragment의 경우

RecyclerView에서 선택될경우 MainActivity의 showDetailFragment로만 add 될 수 있습니다.
뉴스에 대한 상세정보가 나오는 프래그먼트로서 fragment_news_detail을 뷰바인딩하여 만들어집니다.
매개변수로 받아온 데이터값을 이용하여 프래그먼트를 채울 수 있습니다. 뉴스의 저장과 삭제는 별모양의 토글버튼으로 이루어집니다. 뉴스 URL로 검색을 하여 있으면 토글버튼은 활성화됩니다.
