#!/usr/bin/env python
# -*- coding: utf-8 -*-
import httplib
import json
import platform
import ssl
import subprocess
import urllib
import sys


ssl._create_default_https_context = ssl._create_unverified_context
_project_oxford_key_count = 0

class Payload(object):
    def __init__(self, j):
        self.__dict__ = json.loads(j)


class Speech():
    HOST = "https://speech.platform.bing.com"

    def __init__(self, clientId, clientSecret):
        self.__token = ""
        self.instance_id = "1d4b6030-9099-11e0-91e4-0800200c9a66"
        self.UNIQUE_ID = "D4D52672-91D7-4C74-8AD8-42B1D98141A5"
        self.USER_AGENT = "iSpeech.SpeechAPI"
        self.authorize(clientId, clientSecret)

    def authorize(self, clientId, clientSecret):
        httpHeaders = {"Content-type": "application/x-www-form-urlencoded"}
        AccessTokenHost = "oxford-speech.cloudapp.net"
        httpParams = urllib.urlencode(
            {'grant_type': 'client_credentials', 'client_id': clientId, 'client_secret': clientSecret,
             'scope': self.HOST})
        httpPath = "/token/issueToken"
        httpConn = httplib.HTTPConnection(AccessTokenHost)
        try:
            httpConn.request("POST", httpPath, httpParams, httpHeaders)
            response = httpConn.getresponse()
            print response.status
            if (response.status == 200):
                data = response.read()
                httpConn.close()
                Accesstoken = data.decode("UTF-8")
                RespondData = json.loads(Accesstoken)
                self.__token = RespondData['access_token']
                self.Speech_2_Text(str(sys.argv[1]))
            else:
                print "Can't connect!!"
        except:
            print "Authorize POST error"
    def Text_2_Speech(self, text, lang="en-US", female=True):
        template = """
        <speak version='1.0' xml:lang='{0}'>
            <voice xml:lang='{0}' xml:gender='{1}' name='{2}'>
                {3}
            </voice>
        </speak>
        """
        url = self.HOST + "/synthesize"
        httpHeaders = {
            "Content-type": "application/ssml+xml",
            "X-Microsoft-OutputFormat": "riff-16khz-16bit-mono-pcm",
            "Authorization": "Bearer " + self.__token,
            "User-Agent": self.USER_AGENT
        }
        name = self.get_name(lang, female)
        wBody = template.format(lang, "Female" if female else "Male", name, text)

        httpPath = "/synthesize"
        httpConn = httplib.HTTPSConnection("speech.platform.bing.com")
        try:
            httpConn.request("POST", httpPath, wBody, httpHeaders)
            response = httpConn.getresponse()

            if (response.status == 200):
                return response.read()
            else:
                raise response.raise_for_status()
        except:
            print "Text_2_Speech POST error"
    def Speech_2_Text(self, filepath, lang="en-US", samplerate=16000, scenarios="ulm"):
        wFile = open(filepath, 'rb')
        try:
            wBody = wFile.read();
        finally:
            wFile.close()

        httpHeaders = {"Content-type": "audio/wav; samplerate={0}".format(samplerate),
                       "Authorization": "Bearer " + self.__token}

        print platform.system()
        print platform.release()
        httpParams = {
            "version": "3.0",
            "appID": self.UNIQUE_ID,
            "instanceid": self.instance_id,
            "requestid": "1d4b6030-9099-11e0-91e4-0800200c9a66",
            "format": "json",
            "locale": lang,
            "device.os": platform.system() + " " + platform.release(),
            "scenarios": scenarios,
        }
        # Connect to server to recognize the wave binary
        httpPath = "/recognize/query?" + urllib.urlencode(httpParams)
        print httpPath
        httpConn = httplib.HTTPSConnection("speech.platform.bing.com")
        try:
            httpConn.request("POST", httpPath, wBody, httpHeaders)
            response = httpConn.getresponse()

            if (response.status == 200):
                ResponseData = response.read()
                print(ResponseData)
                px = Payload(ResponseData)
                if (px.header["status"] == "success"):

                    httpConn.close()
                    print px.header["lexical"].encode("utf-8")
                    if (px.header["lexical"].encode("utf-8") == "東京 の 天気"):
                        self.iText_2_Speech_callback("東京 の 天気")
                    elif(px.header["lexical"].encode("utf-8") == "お 腹 が 空いた"):
                        self.iText_2_Speech_callback("お腹がすいた")
                    elif(px.header["lexical"].encode("utf-8") == "私 は うんざり です"):
                        self.iText_2_Speech_callback("私はうんざりです")
                    return px.header["lexical"]
                else:
                    sys.stderr.write("[Alex]px.header[status] != success:")
                    print px.header["status"]
                    print px.header["properties"]
                    httpConn.close()
                    return ""
            else:
                global _project_oxford_key_count
                if response.status == 403:
                    _project_oxford_key_count = (_project_oxford_key_count + 1) % 6
                self.authorize("ivoice_speech",
                               rospy.get_param('project_oxford_speech_id' + str(_project_oxford_key_count)))
                sys.stderr.write("[Alex]response status not 200:")
                print response.status
                httpConn.close()
                return ""
        except Exception, e:
            sys.stderr.write("[Alex]Speech_2_Text Exception raise:" + e.message)
            httpConn.close()
            return ""

        httpConn.close()
    def get_name(cls, lang, female):
        template = "Microsoft Server Speech Text to Speech Voice ({0}, {1})"
        person = ""

        if lang == "de-DE":
            person = "Hedda" if female else "Stefan, Apollo"
        elif lang == "en-AU":
            person = "Catherine"
        elif lang == "en-CA":
            person = "Linda"
        elif lang == "en-GB":
            person = "Susan, Apollo" if female else "George, Apollo"
        elif lang == "en-IN":
            person = "Ravi, Apollo"
        elif lang == "en-US":
            person = "ZiraRUS" if female else "BenjaminRUS"
        elif lang == "es-ES":
            person = "Laura, Apollo" if female else "Pablo, Apollo"
        elif lang == "es-MX":
            person = "Raul, Apollo"
        elif lang == "fr-CA":
            person = "Caroline"
        elif lang == "fr-FR":
            person = "Julie, Apollo" if female else "Paul, Apollo"
        elif lang == "it-IT":
            person = "Cosimo, Apollo"
        elif lang == "ja-JP":
            person = "Ayumi, Apollo" if female else "Ichiro, Apollo"
        elif lang == "pt-BR":
            person = "Daniel, Apollo"
        elif lang == "ru-RU":
            person = "Irina, Apollo" if female else "Pavel, Apollo"
        elif lang == "zh-CN":
            person = "Yaoyao, Apollo" if female else "Kangkang, Apollo"
        elif lang == "zh-HK":
            person = "Tracy, Apollo" if female else "Danny, Apollo"
        elif lang == "zh-TW":
            person = "Yating, Apollo" if female else "Zhiwei, Apollo"

        name = template.format(lang, person)
        return name
    def iText_2_Speech_callback(self, data):
        binary = self.Text_2_Speech(data)
        with open("/tmp/voice.wav", "wb") as f:
            f.write(binary)
        sys.stderr.write("[ggg] transfer to voice.wav   " + data + "   ")
        subprocess.call(['aplay', '/tmp/voice.wav'])
        sys.stderr.write("[ggg] finish to play voice.wav   ")
        condition = self.transform_condition("Cloudy".lower());
        print condition
    def transform_condition(self, conditions):
        if conditions == "tornado":
            condition_JP = "竜巻"
        elif conditions == "tropical storm":
            condition_JP = "熱帯暴風雨"
        elif conditions == "hurricane":
            condition_JP = "ハリケーン"
        elif conditions == "severe thunderstorms":
            condition_JP = "重度の雷雨"
        elif conditions == "thunderstorms":
            condition_JP = "雷雨"
        elif conditions == "mixed rain and snow":
            condition_JP = "混合雨と雪"
        elif conditions == "mixed rain and sleet":
            condition_JP = "混合雨やみぞれ"
        elif conditions == "mixed snow and sleet":
            condition_JP = "混合雪とみぞれ"
        elif conditions == "freezing drizzle":
            condition_JP = "凍結霧雨"
        elif conditions == "freezing rain":
            condition_JP = "冷たい雨"
        elif conditions == "showers":
            condition_JP = "シャワー"
        elif conditions == "snow flurries":
            condition_JP = "雪の突風"
        elif conditions == "light snow showers":
            condition_JP = "小雪シャワー"
        elif conditions == "blowing snow":
            condition_JP = "吹いてる雪"
        elif conditions == "snow":
            condition_JP = "雪"
        elif conditions == "hail":
            condition_JP = "雹"
        elif conditions == "sleet":
            condition_JP = "みぞれ"
        elif conditions == "dust":
            condition_JP = "ほこり"
        elif conditions == "foggy":
            condition_JP = "フォギー"
        elif conditions == "haze":
            condition_JP = "ヘイズ"
        elif conditions == "smoky":
            condition_JP = "スモーキー"
        elif conditions == "blustery":
            condition_JP = "大声で話します"
        elif conditions == "windy":
            condition_JP = "強風"
        elif conditions == "cold":
            condition_JP = "コールド"
        elif conditions == "cloudy":
            condition_JP = "曇りました"
        elif conditions == "mostly cloudy":
            condition_JP = "ほとんど曇り"
        elif conditions == "partly cloudy":
            condition_JP = "晴れときどき曇り"
        elif conditions == "clear":
            condition_JP = "クリア"
        elif conditions == "mostly Clear":
            condition_JP = "クリア"
        elif conditions == "sunny":
            condition_JP = "晴れました"
        elif conditions == "fair":
            condition_JP = "フェア"
        elif conditions == "mixed rain and hail":
            condition_JP = "混合雨と雹"
        elif conditions == "hot":
            condition_JP = "ホット"
        elif conditions == "hurricane":
            condition_JP = "ハリケーン"
        elif conditions == "isolated thunderstorms":
            condition_JP = "孤立した雷雨"
        elif conditions == "scattered thunderstorms":
            condition_JP = "散乱雷雨"
        elif conditions == "scattered showers":
            condition_JP = "散乱シャワー"
        elif conditions == "heavy snow":
            condition_JP = "大雪"
        elif conditions == "scattered snow showers":
            condition_JP = "にわか雪"
        elif conditions == "partly cloudy":
            condition_JP = "晴れときどき曇り"
        elif conditions == "thundershowers":
            condition_JP = "雷雨"
        elif conditions == "snow showers":
            condition_JP = "吹雪"
        elif conditions == "isolated thundershowers":
            condition_JP = "孤立した雷シャワー"
        elif conditions == "not available":
            condition_JP = "利用不可"
        else:
            condition_JP = ""

        return condition_JP
if __name__ == '__main__':
    #Speech( "ivoice_speech", "8adc41c36c71418ab04497e6b789ad1f")
    Speech( "ivoice_speech", "060d71f3c5674d3f8f903fc22befaf53")
