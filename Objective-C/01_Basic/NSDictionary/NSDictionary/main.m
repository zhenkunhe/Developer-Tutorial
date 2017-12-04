

#import <Foundation/Foundation.h>

int main(int argc, const char * argv[]) {
    NSMutableDictionary* dic = [[NSMutableDictionary alloc] init];
    [dic setObject:[NSNumber numberWithInt:64] forKey:@"zhenkun"];
    [dic setObject:[NSNumber numberWithInt:65] forKey:@"Alex"];
    [dic setObject:[NSNumber numberWithInt:66] forKey:@"Andy"];
    [dic setObject:[NSNumber numberWithInt:67] forKey:@"Frenk"];
    
    for (NSString* key in dic.allKeys) {    //嚴謹一點,需要宣告取出型態
        NSLog(@"%@的體重是：%d",key,[[dic objectForKey:key] intValue] );//如果key為字串,直接宣告[Dic裡面有的key]也可以尋找到 如直接打@"zhenkun"也可以找到 64
    }//如果key是物件,則需要傳入切確的記憶體位置才可以找到value
    
    /*
    for ((NSString* key , NSNumber* value) in dic) {    //這邊沒有這種寫法
    }
    */
    
    return 0;
}
