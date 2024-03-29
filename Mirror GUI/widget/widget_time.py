# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'UI_Frame.ui'
#
# Created by: PyQt5 UI code generator 5.15.6
#
# WARNING: Any manual changes made to this file will be lost when pyuic5 is
# run again.  Do not edit this file unless you know what you are doing.


from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import *
from PyQt5.QtCore import QTimer
from PyQt5.QtCore import Qt
import datetime


class clock(QWidget):
  def __init__(self):
    self.days  =["월","화","수", '목','금','토', '일']
    super().__init__()
  def setupUi(self, Form, x, y):
      self.widget_border = QtWidgets.QLabel(Form)
      self.widget_border.setGeometry(QtCore.QRect(x+20, y+60, 480, 250))
      self.widget_border.setFrameShape(QtWidgets.QFrame.Box)
      self.widget_border.setText("")
      self.widget_border.setObjectName("widget_border")
      self.time = QtWidgets.QLabel(Form)
      self.time.setGeometry(QtCore.QRect(x+130, y+100, 261, 71))
      font = QtGui.QFont()
      font.setFamily("Century Gothic")
      font.setPointSize(60)
      font.setBold(True)
      font.setWeight(75)
      self.time.setFont(font)
      self.time.setAlignment(QtCore.Qt.AlignCenter)
      self.time.setObjectName("time")
      self.sec = QtWidgets.QLabel(Form)
      self.sec.setGeometry(QtCore.QRect(x+283, y+200, 111, 71))
      font = QtGui.QFont()
      font.setFamily("Century Gothic")
      font.setPointSize(60)
      font.setBold(True)
      font.setWeight(75)
      self.sec.setFont(font)
      self.sec.setAlignment(QtCore.Qt.AlignCenter)
      self.sec.setObjectName("sec")
      self.am_pm = QtWidgets.QLabel(Form)
      self.am_pm.setGeometry(QtCore.QRect(x+400, y+240, 51, 31))
      font = QtGui.QFont()
      font.setFamily("Century Gothic")
      font.setPointSize(17)
      font.setBold(True)
      font.setWeight(75)
      self.am_pm.setFont(font)
      self.am_pm.setObjectName("am_pm")
      self.date = QtWidgets.QLabel(Form)
      self.date.setGeometry(QtCore.QRect(x+90, y+190, 181, 31))
      font = QtGui.QFont()
      font.setFamily("Century Gothic")
      font.setPointSize(20)
      font.setBold(True)
      font.setWeight(75)
      self.date.setFont(font)
      self.date.setAlignment(QtCore.Qt.AlignRight|QtCore.Qt.AlignTrailing|QtCore.Qt.AlignVCenter)
      self.date.setObjectName("date")
      self.week_day = QtWidgets.QLabel(Form)
      self.week_day.setGeometry(QtCore.QRect(x+190, y+230, 81, 31))
      font = QtGui.QFont()
      font.setFamily("Century Gothic")
      font.setPointSize(20)
      font.setBold(True)
      font.setWeight(75)
      self.week_day.setFont(font)
      self.week_day.setAlignment(QtCore.Qt.AlignRight|QtCore.Qt.AlignTrailing|QtCore.Qt.AlignVCenter)
      self.week_day.setObjectName("week_day")
      self.retranslateUi_time(Form)


      self.timer_init(Form)
      QtCore.QMetaObject.connectSlotsByName(Form)

  def timeout_fun(self): #시간 재설정 함수
    now = datetime.datetime.now()
    tstr = ""
    ts = ""
    if(now.hour<10):
      tstr = "0"+ str(now.hour)
    else:
      tstr += str(now.hour)
    tstr += " : "
    if(now.minute<10):
      tstr += "0"
      tstr += str(now.minute)
    else:
      tstr += str(now.minute)

    if(now.second<10):
      ts += "0"
      ts += str(now.second)
    else:
      ts += str(now.second)
    self.time.setText(tstr)
    self.sec.setText(ts)
    mdstr = str(now.month) + "월 "+ str(now.day)+"일"
    self.date.setText(mdstr)
    self.week_day.setText(self.days[now.weekday()])

      #print("time cnt is %d" %self.time_cnt)

  def timer_init(self,Form): #시계 작동 타이머 이벤트
      print("시작")
      self.timer = QTimer(Form)
      print(self.timer)
      self.timer.start(1000)
      self.timer.timeout.connect(self.timeout_fun)

    


  def retranslateUi_time(self, Form): #시계 위젯 화면 초기화
    now = datetime.datetime.now()
    tstr = ""
    ts = ""
    if(now.hour<10):
      tstr = "0"+ str(now.hour)
    else:
      tstr += str(now.hour)
    tstr += " : "
    if(now.minute<10):
      tstr += "0"
      tstr += str(now.minute)
    else:
      tstr += str(now.minute)

    if(now.second<10):
      ts += "0"
      ts += str(now.second)
    else:
      ts += str(now.second)
    self.time.setText(tstr)
    self.sec.setText(ts)
    mdstr = str(now.month) + "월 "+ str(now.day)+"일"
    self.date.setText(mdstr)
    self.week_day.setText(self.days[now.weekday()])

