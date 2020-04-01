# Chapter2-Homework

## 作业一

因为onSaveInstance在onDestroy之前调用，所以需要一个变量isDestroy来标识是否被销毁

## 作业二

直接使用递归，通过getChildCount得到viewGroup内的view的数量，再通过getChildAt得到第几个view

## 作业三

- 先通过pullParser对data.xml的文件进行读取，存为messages
- 然后实现recyclerView，实现自己的adapter，并把读取的数据和监听器传入adapter
- 在adapter里，通过viewHolder对每个item进行数据填充
- 设置recyclerView的监听事件，通过intent里的bundle，将电机的item的index传到另一个页面，进行显示

**我的apk为my_homework.apk，在此目录下**