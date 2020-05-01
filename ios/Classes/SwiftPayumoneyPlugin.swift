import Flutter
import UIKit
import PlugNPlay
import CommonCrypto

public class SwiftPayumoneyPlugin: NSObject, FlutterPlugin {
        
    
    let controller: FlutterViewController
    
    var flutterResult: FlutterResult?
    
    init(controller: FlutterViewController) {
        self.controller = controller
    }
    
    
    public static func register(with registrar: FlutterPluginRegistrar) {
        
        let channel = FlutterMethodChannel(name: "payumoney_plugin", binaryMessenger: registrar.messenger())
        
        let storyboard : UIStoryboard? = UIStoryboard.init(name: "Main", bundle: nil);
        
        let viewController: UIViewController? = storyboard!.instantiateViewController(withIdentifier: "FlutterViewController")
        
        let instance = SwiftPayumoneyPlugin(controller: viewController as! FlutterViewController)
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        self.flutterResult = result
        if(call.method.elementsEqual("getResult")){
            
            
            let args = call.arguments as? NSDictionary
            
            var txnParam = PUMTxnParam()
            
            txnParam.phone = args!["phone"] as? String
            txnParam.email = args!["email"] as? String
            txnParam.amount = args!["amount"] as? String
            txnParam.environment = args!["environment"] as? String == "sandbox" ? PUMEnvironment.test : PUMEnvironment.production;
            txnParam.firstname = args!["firstname"] as? String;
            txnParam.key = args!["merchant_key"] as? String;
            txnParam.merchantid = args!["merchant_id"] as? String;
            txnParam.txnID = args!["txnid"] as? String;
            txnParam.surl = args!["surl"] as? String
            txnParam.furl = args!["furl"] as? String;
            txnParam.productInfo = args!["productinfo"] as? String
            txnParam.udf1 = args!["udf1"] as? String;
            txnParam.udf2 = args!["udf2"] as? String;
            txnParam.udf3 = args!["udf3"] as? String;
            txnParam.udf4 = args!["udf4"] as? String;
            txnParam.udf5 = args!["udf5"] as? String;
            txnParam.udf6 = args!["udf6"] as? String;
            txnParam.udf7 = args!["udf7"] as? String;
            txnParam.udf8 = args!["udf8"] as? String;
            txnParam.udf9 = args!["udf9"] as? String;
            txnParam.udf10 = args!["udf10"] as? String;
            txnParam.hashValue = args!["hash"] as? String
            
            
            let rootViewController:UIViewController! = UIApplication.shared.keyWindow?.rootViewController
            
            PlugNPlay.presentPaymentViewController(withTxnParams: txnParam, on: rootViewController) { (dict, error, value) in
                result(dict)
                
            }
        }else{
            result(FlutterMethodNotImplemented);
            
        }
    }
    
}
