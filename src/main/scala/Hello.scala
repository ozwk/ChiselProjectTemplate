package Hello

import Chisel._

class HelloModule extends Module {
  val io = new Bundle {
    val out = UInt(OUTPUT, 8)
  }
  io.out := UInt(42)
}

class HelloTests(c: HelloModule) extends Tester(c) {
  step(1)
  expect(c.io.out, 42)
}

object HelloModule {
  def main(args: Array[String]): Unit = {

    val args_ = if(args.size > 0 && args(0) == "--test" ){
      Array("--backend", "c", "--genHarness", "--compile", "--test" ,"--targetDir" , "./cpp")
    }else{
      Array("--backend", "v", "--compile", "--targetDir" , "./verilog")
    }

    HelloTest(args_, () => Module(new HelloModule())) {
      c => new NiceModuleTest(c)
          }
  }
}