
with (import <nixpkgs> {});

stdenv.mkDerivation rec {
  name = "system-spec-clj-${version}";
  version = "0.1";
  src = ./.;

  buildInputs = [ 
    boot
  ];
}
